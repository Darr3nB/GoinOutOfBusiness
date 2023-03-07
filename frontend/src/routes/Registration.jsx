import {useNavigate} from "react-router-dom";
import {utility} from "../utility.js";
import {useState} from "react";

export default function Registration() {
    // TODO validate field
    const navigate = useNavigate();
    const [uploadedImage, setImage] = useState(null);

    const performRegistration = async (event) => {
        event.preventDefault();
        const formData = new FormData(event.currentTarget);
        const profilePicture = uploadedImage === null ? utility.questionMarkPicture : uploadedImage;

        if (!utility.validateEmail(formData.get("email-field"))) {
            // TODO deactivate reg button, and notify user about invalid email
            alert("Invalid e-mail format.");
            return;
        }

        await utility.apiPostWithDictionary(`/user/registration`, {
            'email': formData.get("email-field"),
            'username': formData.get("username-field"),
            'password': formData.get("password-field"),
            'passwordAgain': formData.get("password-again-field"),
            'dateOfBirth': null,
            'profilePicture': profilePicture,
        }).then(response => {
            if (response.ok) {
                navigate(0);
            }
        })
    }

    const uploadImage = async (event) => {
        const file = event.target.files[0];
        const base64 = await utility.convertBase64(file);

        setImage(base64);
    }

    return (
        <div>
            <form onSubmit={event => performRegistration(event)}>
                <input type="text" placeholder="Email address" name="email-field"/>
                <input type="text" placeholder="Username" name="username-field"/>
                <input type="text" placeholder="Password" name="password-field"/>
                <input type="text" placeholder="Password again" name="password-again-field"/>
                {/*TODO Date of birth*/}
                <input type="file" accept=".jpg, .jpeg, .png" onChange={event => uploadImage(event)}/>
                <button>Registration</button>
            </form>

        </div>
    );
}