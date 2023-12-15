const express = require("express");
const { graphqlHTTP } = require("express-graphql");
const { buildSchema } = require("graphql");
const fs = require("fs");

const DATABASE_FILE =  "../database.json";
const PORT =  3000;


function readDatabase() {
  try {
    const rawData = fs.readFileSync(DATABASE_FILE);
    return JSON.parse(rawData);
  } catch (error) {
    console.error("Erro ao ler o arquivo de banco de dados:", error);
    return null;
  }
}

const data = readDatabase();

const schema = buildSchema(`
  type User {
    id: ID!
    name: String
    age: Int
  }

  type Music {
    id: ID!
    name: String
    author: String
    playlistId: ID
  }

  type Playlist {
    id: ID!
    name: String
    userId: ID
  }

  type Query {
    findUser(id: ID!): User
    listUsers: [User]
    findMusic(id: ID!): Music
    listMusics: [Music]
    findPlaylist(id: ID!): Playlist
    listPlaylists: [Playlist]
  }
`);

const root = {
  findUser: ({ id }) => data?.users?.find((user) => user.id === id),
  listUsers: () => data?.users,
  findMusic: ({ id }) => data?.musics?.find((music) => music.id === id),
  listMusics: () => data?.musics,
  findPlaylist: ({ id }) =>
    data?.playlists?.find((playlist) => playlist.id === id),
  listPlaylists: () => data?.playlists,
};

const app = express();

app.use(
  "/graphql",
  graphqlHTTP({
    schema: schema,
    rootValue: root,
    graphiql: true,
    customFormatErrorFn: (error) => ({
      message: error.message,
      locations: error.locations,
      stack: error.stack ? error.stack.split('\n') : [],
      path: error.path
    })
  })
);

app.listen(PORT, () => {
  console.log(`GraphQL server running at http://localhost:${PORT}/graphql`);
});
