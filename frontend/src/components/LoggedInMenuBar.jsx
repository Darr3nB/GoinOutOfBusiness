import {Link, useNavigate} from "react-router-dom";
import {utility} from "../utility.js";

export default function LoggedInMenuBar({user}) {
    const navigate = useNavigate();

    const logout = async (event) => {
        event.preventDefault();

        await utility.apiGet(`/user/logout`)
            .then(response => {
                if (response.ok) {
                    localStorage.removeItem('user');
                    navigate(0);
                }
            });
    }

    return (
        <span>
            <Link to={`/profile`} className="button-link margin">Profile</Link>
            <button className="login-btn button-link margin" onClick={event => logout(event)}>Logout</button>
        </span>
    );
}
