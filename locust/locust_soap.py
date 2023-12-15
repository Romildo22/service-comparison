from locust import HttpUser, task, between


class MyUser(HttpUser):
    wait_time = between(2, 5)  # Time between consecutive requests

    @task
    def get_users(self):
        endpoint = f"/users"
        self.client.get(endpoint)


# locust -f locust_rest.py
