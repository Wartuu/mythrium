import { useState } from "react";
import Spinner from "../components/spinner";
import "../styles/login.scss"
import { API, sendRequest } from "../config/api";
import storageManager from "../config/configuration";
import { Link } from "react-router-dom";


async function attemptLogin(username, password) {
    let output = {success: undefined, information: undefined};

    let input = {
        username: username,
        password: password
    };

    let response = await sendRequest(API.LOGIN_URL, input);

    let data;

    if(!response.ok) {
        output.success = false;
        output.information = "Failed to send request";
        return output;
    } else {
        // contains success: bool, information: bool, token: bool
        data = await response.json();
    }

    output.success = data.success;
    output.information = data.information;

    if(output.success) {
        storageManager.setValue("session", data.token);
    }
}

const LoginPage = () => {

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const [loading, setLoading] = useState("hidden");

    const [showInfo, setShowInfo] = useState("hidden");
    const [loginInfo, setLoginInfo] = useState("");
    const [infoType, setInfoType] = useState("success");


    return(
        <>
            <div id="cards">
                <div className="card no-hover">
                    <div className="header">login</div>

                    <input type="text" name="username" value={username} onChange={e => setUsername(e.target.value)} className="credentials" placeholder="Username"/>
                    <input type="password" name="password" value={password} onChange={e => setPassword(e.target.value)} className="credentials" placeholder="Password"/>
                    <input type="button" value={"login"} className="button-action credentials" onClick={ async () => {
                        setShowInfo("hidden");
                        setLoading("visible");
                        let info = await attemptLogin(username, password);
                        
                        setLoginInfo(info.information);

                        if(info.success) setInfoType("success")
                        else setInfoType("error");
                        
                        setLoading("hidden");
                        setShowInfo("visible");
                    }
                        
                    }/>

                    <div className="login-spinner" style={{visibility: loading}}>
                        <Spinner/>
                    </div>

                    <div className={`login-message ${infoType}-color`} style={{visibility: showInfo}}>
                        {loginInfo}
                    </div>

                    <Link to={"/register"} className="comment-text">
                        Create an account
                    </Link>

                </div>
            </div>
        </>
    )
}

export default LoginPage;