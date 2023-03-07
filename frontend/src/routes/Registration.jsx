import {useNavigate} from "react-router-dom";
import {utility} from "../utility.js";
import {useState} from "react";
import Button from "../components/Button";

export default function Registration() {
    const navigate = useNavigate();
    const [uploadedImage, setImage] = useState(null);
    const [regBtn, setRegBtn] = useState(0);
    const [emailPtagText, setEmailPtagText] = useState("ASDASD");
    const [emailAlreadyExists, setEmailAlreadyExists] = useState(false);


    function passwordMatcher(password, password2) {
        return password === password2;
    }

    const performRegistration = async (event) => {
        event.preventDefault();
        const formData = new FormData(event.currentTarget);
        const profilePicture = uploadedImage === null ? utility.questionMarkPicture : uploadedImage;

        if (!passwordMatcher(formData.get("password-field"), formData.get("password-again-field"))) {
            alert("Passwords must match!");
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

    const checkEmail = async (email) => {
        await utility.apiGet(`/user/validate-email-for-register/${email}`)
            .then(response => {
                if (response.ok) {
                    setEmailAlreadyExists(true);
                } else if (response.status === 403) {
                    setEmailAlreadyExists(false);
                }
            });

        if (email.length <= 0) {
            setEmailPtagText("");
            setRegBtn(0);
        } else if (!utility.validateEmail(email) || emailAlreadyExists) {
            setEmailPtagText("Invalid e-mail or is already registered.");
            setRegBtn(2);
        } else {
            setEmailPtagText("E-mail available.");
            setRegBtn(1);
        }
    }

    return (
        <div>
            <form onSubmit={event => performRegistration(event)}>
                <input type="text" placeholder="Email address" name="email-field" onInput={event => checkEmail(event.target.value)} minLength="3"/>
                <p className={regBtn === 0 ? 'invisible-email-p' : regBtn === 1 ? 'valid-email-p' : 'error-email-p'}>{emailPtagText}</p>
                <input type="text" placeholder="Username" name="username-field" minLength="3"/>
                <input type="text" placeholder="Password" name="password-field" minLength="3"/>
                <input type="text" placeholder="Password again" name="password-again-field" minLength="3"/>
                {/*TODO Date of birth*/}
                <input type="file" accept=".jpg, .jpeg, .png" onChange={event => uploadImage(event)}/>
                <Button text="Registration" status={!regBtn}/>
            </form>

        </div>
    );
}
