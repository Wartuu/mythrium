import React from "react";

import "../styles/home.scss"
import { Link } from "react-router-dom";



class HomePage extends React.Component {

    constructor(props) {
        super(props);

        this.variables = {
            MOTTO: "Forever free and open-source"
        },

        this.state = {
            motto: this.variables.MOTTO[0]
        }

        this.animateMotto = this.animateMotto.bind(this);
    }

    componentDidMount() {
        this.animateMotto();
    }
    
    componentWillUnmount() {
        clearInterval(this.intervalId);
    }

    animateMotto() {
        let currentChar = 1;

        if (!(this.variables.MOTTO.length > 1)) {
            return;
        }

        this.intervalId = setInterval(() => {

            if (currentChar < this.variables.MOTTO.length) {
                this.setState({
                    motto: this.state.motto + this.variables.MOTTO[currentChar],
                });

            } else {
                clearInterval(this.intervalId);
                return;
            }

            currentChar++;
        }, 150);

    }

    render() {
        return(
            <>
                <div id="page" className="home-bg">
                    <Link to={"/login"} className="button-link log-in-url">
                        <button className="button-comment" id="log-in">
                            Log in
                        </button>
                    </Link>

                    <div className="center-flex">Mythrium</div>
                    <div className="center-flex" id="motto">{this.state.motto}</div>


                    <div id="cards">
                        <div id="about" className="card">
                            <div className="header">open-source</div>

                            <div className="details">
                                our project is actively developed at our <a href="https://github.com/wartuu/mythrium" className="href">repository</a> at github.com
                            </div>


                        </div>
                        <div id="join" className="card">

                            <div className="header">Join us!</div>
                            <div className="details">
                                Our project provides free storage for all of your notes! <br/><br/>
                                With easy to use editor you can edit, create, upload all files
                            </div>

                            <Link to={"/register"} className="button-link" id="register">
                                <button className="button-action filler-button register">
                                    Join now!
                                </button>
                            </Link>

                        </div>
                        <div id="share" className="card">
                            <div className="header">Want to share?</div>
                            <div className="details">
                                You can share all of your notes and files creating a single link for everyone or just for your friends! <br/> <br/>
                                Our application supports <br/>Real-time collaboration in editor!
                            </div>
                        </div>
                    </div>



                </div>
            </>
        )

    }
}

export default HomePage;