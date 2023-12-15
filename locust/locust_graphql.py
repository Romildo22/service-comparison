from locust import HttpUser, task, between


class GraphQLUser(HttpUser):
    wait_time = between(1, 3)  # Time between consecutive requests

    @task
    def query_user(self):
        user_id = "87c2e78c-847b-426f-bc97-9e337011613d"

        query = """
        query {
            findUser(id: "%s") {
                id
                name
                age
            }
        }
        """ %user_id

        # Make the GraphQL request
        response = self.client.get("/graphql", json={"query": query}, headers={"Content-Type": "application/json"})


      

