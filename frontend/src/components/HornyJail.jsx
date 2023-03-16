import {useEffect} from "react";

export default function HornyJail() {
    useEffect(() => {
        setTimeout(() => {
            window.location.href = "https://www.codecool.com";
        }, 2500)
    }, [])

    return (
        <div className="middle-text horny-div">
            <h1>Horny are ya?</h1>
            <img src="../src/assets/horny-jail-bonk.gif" alt="horny-jail-bonk-meme"/>
            <p className="horny-p-tag">No horny! Learn coding!</p>
        </div>
    );
}
