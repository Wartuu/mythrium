package main

import (
	"flag"
	"fmt"
	"os"
	"os/signal"
	"time"

	"golang.org/x/net/websocket"
)

// config is default setting
// if other flags are defined (eq address) they will be used instead

var config string
var address string

func init() {
	flag.StringVar(&config, "config", "config.json", "path to configuration file")
	flag.StringVar(&address, "address", "ws://127.0.0.1:7070", "address to worker manager")

	flag.Parse()
}

func main() {
	connect()

	interrupt := make(chan os.Signal, 1)
	signal.Notify(interrupt, os.Interrupt)

	for {
		select {
		case <-interrupt:
			return

		}
	}
}

func connect() {
	connection, err := websocket.Dial(address, "", "http://localhost")

	if err != nil {
		fmt.Println(err.Error())
	}
	go func() {
		for {
			_, err := connection.Write([]byte("Hello, World!"))

			if err != nil {
				fmt.Println(err.Error())
			} else {
				fmt.Println("sent data")
			}

			time.Sleep(time.Second)
		}
	}()
}
