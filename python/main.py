from flask import Flask, jsonify
import json

app = Flask(__name__)
DATABASE_FILE = "./database.json"  # Update this path if needed

# Function to read the JSON database
def read_database():
    try:
        with open(DATABASE_FILE, 'r') as file:
            return json.load(file)
    except IOError as e:
        print(f"Error reading the database file: {e}")
        return None

data = read_database()

@app.route('/users', methods=['GET'])
def list_users():
    return jsonify(data['users'])

@app.route('/user/<user_id>', methods=['GET'])
def find_user(user_id):
    user = next((u for u in data['users'] if u['id'] == user_id), None)
    return jsonify(user) if user else ('', 404)

@app.route('/musics', methods=['GET'])
def list_musics():
    return jsonify(data['musics'])

@app.route('/music/<music_id>', methods=['GET'])
def find_music(music_id):
    music = next((m for m in data['musics'] if m['id'] == music_id), None)
    return jsonify(music) if music else ('', 404)

@app.route('/playlists', methods=['GET'])
def list_playlists():
    return jsonify(data['playlists'])

@app.route('/playlist/<playlist_id>', methods=['GET'])
def find_playlist(playlist_id):
    playlist = next((p for p in data['playlists'] if p['id'] == playlist_id), None)
    return jsonify(playlist) if playlist else ('', 404)

if __name__ == '__main__':
    app.run(debug=True, port=3000)
