import {Link} from "react-router-dom";

export default function Footer() {
    return (
        <div className="footer">
            Copyright <Link to="/developers/zsoos">Z-SooS</Link> - <Link to="/developers/darren">Darren</Link> &
            fake@email.com
        </div>
    );
}