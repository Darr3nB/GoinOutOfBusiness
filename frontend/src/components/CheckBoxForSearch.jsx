export default function checkBoxForSearch({id, value}){
    return (
        <div>
            <label htmlFor="">{value}</label>
            <input type="checkbox" id={`type-${id}`} name={`type-${id}`} value={value}/>
        </div>
    );
}
