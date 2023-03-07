import {Link} from "react-router-dom";
import {useState} from "react";
import LoggedInMenuBar from "./LoggedInMenuBar";
import LoggedOff from "./LoggedOff";

export default function MenuLayout() {
    const [loggedIn, setLoggedIn] = useState(false);
    // TODO Check if logged in. If logged in, pass the user data to LoggedInMenuBar
    // TODO add more buttons, like search, or products.

    return (
        <div>
            <Link to={`/`} className="button-link">Home</Link>
            {loggedIn ? <LoggedInMenuBar/> : <LoggedOff/>}
        </div>
    );
}
