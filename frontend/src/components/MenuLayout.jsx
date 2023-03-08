import {Link} from "react-router-dom";
import {useEffect, useState} from "react";
import LoggedInMenuBar from "./LoggedInMenuBar";
import LoggedOff from "./LoggedOff";

export default function MenuLayout() {
    const [loggedIn, setLoggedIn] = useState({});
    // TODO Check if logged in. If logged in, pass the user data to LoggedInMenuBar
    // TODO add more buttons, like search, or products.


    useEffect(() => {
        return () => {
            if (localStorage.hasOwnProperty('user')){
                setLoggedIn(JSON.parse(localStorage.getItem('user')));
            }else {
                setLoggedIn(false);
            }
        };
    }, []);


    return (
        <div>
            <Link to={`/`} className="button-link">Home</Link>
            {!loggedIn ? <LoggedOff/> : <LoggedInMenuBar/>}
        </div>
    );
}
