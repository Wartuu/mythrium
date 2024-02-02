import { useState } from "react";
import { API, sendRequest } from "../config/api";
import '../styles/login.scss'
import Spinner from "../components/spinner";
import storageManager from "../config/configuration";
import { Link } from "react-router-dom";

async function attemptRegister(email, username, password) {
    let output = {success: undefined, information: undefined};

    let input = {
        email: email,
        username: username,
        password: password
    };
    
    let response = await sendRequest(API.REGISTER_URL, input);

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
        storageManager.setValue("session", data.token, true);
    }
}

const RegisterPage = () => {

    const [email, setEmail] = useState("");
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const [loading, setLoading] = useState("hidden");

    const [showInfo, setShowInfo] = useState("hidden");
    const [registerInfo, setRegisterInfo] = useState("");
    const [infoType, setInfoType] = useState("success");



    //TODO: add username input
    
    return(
        <>
            <div id="cards">
                <div className="card no-hover">
                    <div className="header">register</div>

                    <input type="email" name="email" value={email} onChange={e => setEmail(e.target.value)} className="credentials" placeholder="Email"/>
                    <input type="text" name="username" value={username} onChange={e => setUsername(e.target.value)} className="credentials" placeholder="Username"/>
                    <input type="password" name="password" value={password} onChange={e => setPassword(e.target.value)} className="credentials" placeholder="Password"/>
                    
                    <input type="button" value={"register"} className="button-action credentials" onClick={async () => {
                        setShowInfo("hidden");
                        setLoading("visible");
                        let info = await attemptRegister(email, username, password);

                        setRegisterInfo(info.information);

                        if(info.success) setInfoType("success");
                        else setInfoType("error");

                        setLoading("hidden");
                        setShowInfo("visible");
                    }}/>

                    <div className="login-spinner" style={{visibility: loading}}>
                        <Spinner/>
                    </div>

                    <div className={`login-message ${infoType}-color`} style={{visibility: showInfo}}>
                        {registerInfo}
                    </div>

                    <Link to={"/login"} className="login-switch">
                        Login instead
                    </Link>
                </div>
            </div>
        </>
    )
}

export default RegisterPage;