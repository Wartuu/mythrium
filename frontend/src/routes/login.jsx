import { useState } from "react";
import Spinner from "../components/spinner";
import "../styles/login.scss"


function attemptLogin(username, password) {
    console.log(username + " : " + password)

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
                <div className="card">
                    <div className="header">login</div>

                    <input type="text" name="username" value={username} onChange={e => setUsername(e.target.value)} className="credentials" placeholder="Username"/>
                    <input type="password" name="password" value={password} onChange={e => setPassword(e.target.value)} className="credentials" placeholder="Password"/>
                    <input type="button" value={"login"} className="button-action credentials" onClick={attemptLogin(username, password)}/>

                    <div className="login-spinner" style={{visibility: loading}}>
                        <Spinner/>
                    </div>

                    <div className={`login-message ${infoType}-color`} style={{visibility: showInfo}}>
                        {loginInfo}
                    </div>
                </div>
            </div>
        </>
    )
}

export default LoginPage;