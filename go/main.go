package main

import (
	"fmt"
	"log"
	"net"
	// Importando pacotes gRPC e protobuf
	api "github.com/iaggo/go/proto"
	"google.golang.org/grpc"
)

func main() {

	LoadDatabase("../database.json")

	lis, err := net.Listen("tcp", ":8001")
	if err != nil {

		log.Fatalf("cannot create listener: %s", err)
	}

	serverRegistrar := grpc.NewServer()

	service := &APIServer{}

	api.RegisterAPIServer(serverRegistrar, service)

	fmt.Println("server at localhost:8001")
	err = serverRegistrar.Serve(lis)
	if err != nil {
		log.Fatalf("impossible to serve: %s", err)
	}
}
