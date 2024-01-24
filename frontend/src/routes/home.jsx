import React from "react";

import "../styles/home.scss"
import { Link } from "react-router-dom";



class HomeRoute extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            motto: "M"
        }

        this.animateMotto = this.animateMotto.bind(this);
    }

    componentDidMount() {
        this.animateMotto();
    }
    
    componentWillUnmount() {
        clearInterval(this.intervalID);
    }

    animateMotto() {
        const MOTTO = "Make your myths real";
        let currentChar = 1;

        this.intervalId = setInterval(() => {

            if (currentChar < MOTTO.length) {
                this.setState({
                    motto: this.state.motto + MOTTO[currentChar],
                });
            } else {
                clearInterval(this.intervalId);
            }

            console.log(this.state.motto);
            currentChar++;
        }, 100);
    }

    render() {
        return(
            <>
                <div id="page">
                    <Link to={"/login"}>
                        <button className="button-action" id="log-in">
                            Log in
                        </button>
                    </Link>

                    <div className="center-flex">Mythrium</div>
                    <div className="center-flex" id="motto">{this.state.motto}</div>


                    <div id="cards">
                        <div id="about" className="card"></div>
                        <div id="join" className="card"></div>
                        <div id="updates" className="card"></div>
                    </div>



                </div>
            </>
        )

    }
}

export default HomeRoute;