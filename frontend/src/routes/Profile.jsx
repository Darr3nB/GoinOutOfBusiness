import {useNavigate} from "react-router-dom";
import {useEffect} from "react";

export default function Profile() {
    const navigate = useNavigate();
    const user = JSON.parse(localStorage.getItem('user'));

    useEffect(() => {
        return () => {
            if (!user){
                navigate("/");
            }
        };
    }, [user]);


    return (
        <div className="middle-text white-text big-letters">
            <h1 className="fancy-font">Profile page</h1>
            <img className="profile-picture" src={user?.profilePicture} alt="User's profile picture."/>
            <div>E-mail address: {user?.email}</div>
            <div>Registration date: {user?.dateOfRegistration}</div>
            <div>Birthday: {user?.dateOfBirth}</div>
            <div>Role: {user?.role}</div>
            <div>Registration id: {user?.id}</div>
        </div>
    );
}
