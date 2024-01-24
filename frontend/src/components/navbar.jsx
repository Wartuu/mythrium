import { Link } from "react-router-dom";
import "../styles/navbar.scss"

function NavBar() {
    return (
        <div id="navbar">
            <Link to={"/"}>
                <button className="navbar-button">/</button>
            </Link>
            <Link to={"/note/edit"}>
                <button className="navbar-button">/note/edit</button>
            </Link>
        </div>


    )


}

export default NavBar;