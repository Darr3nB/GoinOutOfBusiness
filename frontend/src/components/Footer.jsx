import {Link} from "react-router-dom";

export default function Footer() {
    return (
        <div className="footer white-text">
            Copyright <Link to="/developers/zsoos"><span className="white-text">Z-SooS</span></Link> - <Link to="/developers/darren"><span className="white-text">Darren</span></Link> &
            fake@email.com
        </div>
    );
}