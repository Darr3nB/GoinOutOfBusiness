import {Link} from "react-router-dom";
import {useEffect, useState} from "react";
import LoggedInMenuBar from "./LoggedInMenuBar";
import LoggedOff from "./LoggedOff";

export default function MenuLayout() {
    const [loggedIn, setLoggedIn] = useState({});


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
            <h1>Welcome {!loggedIn ? "visitor" : loggedIn.email}!</h1>
            <Link to={`/`} className="button-link">Home</Link>
            <Link to={`/about-us`} className="button-link">About us</Link>
            {!loggedIn ? <LoggedOff/> : <LoggedInMenuBar user={loggedIn}/>}
        </div>
    );
}
