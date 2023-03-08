import {Link, useNavigate} from "react-router-dom";
import {utility} from "../utility.js";

export default function LoggedInMenuBar() {
    const navigate = useNavigate();

    const logout = async (event) => {
        event.preventDefault();

        await utility.apiGet(`/user/logout`)
            .then(response => {
                if (response.ok) {
                    navigate(0);
                }
            });
    }

    return (
        <span>
            <Link to={`/profile`} className="button-link">Profile</Link>
            <button className="button-link" onClick={event => logout(event)}>Logout</button>
        </span>
    );
}
