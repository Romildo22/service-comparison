import time

from google.protobuf import empty_pb2
from locust import User, task, between, events
import grpc
import grpc_pb2, grpc_pb2_grpc

class GrpcClient:
    def __init__(self, host, port):
        self.channel = grpc.insecure_channel(f"{host}:{port}")
        self.stub = grpc_pb2_grpc.APIStub(self.channel)

    def list_users(self):
        request = empty_pb2.Empty()
        start_time = time.time()

        try:
            response = self.stub.ListUsers(request)
            total_time = int((time.time() - start_time) * 1000)  # Time in milliseconds
            events.request.fire(request_type="grpc", name="list_users", response_time=total_time, response_length=len(str(response)), exception=None)
            return response
        except grpc.RpcError as e:
            total_time = int((time.time() - start_time) * 1000)  # Time in milliseconds
            events.request.fire(request_type="grpc", name="list_users", response_time=total_time, response_length=0, exception=e)
            raise e

    def close(self):
        self.channel.close()

    def __enter__(self):
        return self

    def __exit__(self, exc_type, exc_value, traceback):
        self.close()

class GrpcUser(User):
    wait_time = between(1, 5)
    abstract = True

class ApiUser(GrpcUser):
    @task
    def list_users(self):
        with GrpcClient("localhost", 8001) as client:  # Replace with your gRPC server details
            try:
                response = client.list_users()
                pass
                #print("Received response:", response)
            except Exception as e:
                pass
                #print("Error during request:", e)

if __name__ == "__main__":
    pass  # This script should be run using the Locust command line
