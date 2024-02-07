import { useState } from "react";
import Spinner from "../components/spinner";
import "../styles/login.scss"
import { API, sendRequest } from "../config/api";
import storageManager from "../config/configuration";
import { Link } from "react-router-dom";
import Window from "../components/window";


async function attemptLogin(username, password, remember) {
    let output = {success: undefined, information: undefined};

    let input = {
        username: username,
        password: password,
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
        storageManager.setValue("token", data.token, remember);
        storageManager.setValue("token", undefined, !remember);
    }
}

const LoginPage = () => {

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [remember, setRemember] = useState(false);

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
                    <div className="credentials">
                        <input type="checkbox" name="remember" checked={remember} onChange={() => setRemember(!remember)}/>
                        <span className="comment">
                            remember me
                        </span>
                    </div>
                    <input type="button" value={"login"} className="button-action credentials" onClick={ async () => {
                        setShowInfo("hidden");
                        setLoading("visible");
                        let info = await attemptLogin(username, password, remember);
                        
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

                    <Link to={"/register"} className="login-switch">
                        Create an account
                    </Link>

                </div>
            </div>


            <Window title={"testing application"} element={"app testing"}/>
        </>
    )
}

export default LoginPage;