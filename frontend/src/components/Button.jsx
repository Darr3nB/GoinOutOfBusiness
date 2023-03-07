export default function Button({text, status}){
    return (
        <button disabled={status}>{text}</button>
    );
}
