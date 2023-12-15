from locust import HttpUser, task, between


class MyUser(HttpUser):
    wait_time = between(2, 5)  # Time between consecutive requests

    @task
    def get_user(self):
        user_id = "87c2e78c-847b-426f-bc97-9e337011613d"
        endpoint = f"/user/{user_id}"
        self.client.get(endpoint)

    @task
    def get_users(self):
        endpoint = f"/users"
        self.client.get(endpoint)

    @task
    def get_music(self):
        music_id = "8fbedbe9-e394-4015-96ab-5bd8e9b51d2b"
        endpoint = f"/music/{music_id}"
        self.client.get(endpoint)

    @task
    def get_musics(self):
        endpoint = f"/musics"
        self.client.get(endpoint)

    @task
    def get_playlist(self):
        playlist_id = "ae29499b-eb49-47ad-88a4-3b4467ba13d8"
        endpoint = f"/playlist/{playlist_id}"
        self.client.get(endpoint)

    @task
    def get_playlists(self):
        endpoint = f"/playlists"
        self.client.get(endpoint)

# locust -f locust_rest.py
