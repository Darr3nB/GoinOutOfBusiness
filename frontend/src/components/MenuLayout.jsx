import {Link} from "react-router-dom";
import {useEffect, useState} from "react";
import LoggedInMenuBar from "./LoggedInMenuBar";
import LoggedOff from "./LoggedOff";

export default function MenuLayout() {
    const [loggedIn, setLoggedIn] = useState({});


    useEffect(() => {
        return () => {
            if (localStorage.hasOwnProperty('user')) {
                setLoggedIn(JSON.parse(localStorage.getItem('user')));
            } else {
                setLoggedIn(false);
            }
        };
    }, []);


    return (
        <div>
            <h1 className="fancy-font white-text middle-text">Welcome {!loggedIn ? "visitor" : loggedIn.email} to Going
                out of Business web shop!</h1>
            <div className="menu-bar middle-text">
                <Link to={`/`} className="button-link margin">Home</Link>
                <Link to={`/about-us`} className="button-link margin">About us</Link>
                {!loggedIn ? <LoggedOff/> : <LoggedInMenuBar user={loggedIn}/>}
            </div>
        </div>
    );
}
