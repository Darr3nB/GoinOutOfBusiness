import ProductCard from "./ProductCard";

export default function HomePage() {
    const dummyData = [
        {
            'id': 0,
            'name': 'Bubuka',
            'description': 'very useful much wow',
            'price': 99999,
            'type': 'Other',
            'inventory': 5
        },
        {
            'id': 1,
            'name': 'Cucuka',
            'description': 'very useful much wow',
            'price': 5555,
            'type': 'Other',
            'inventory': 1
        },
        {
            'id': 1,
            'name': 'Cucuka',
            'description': 'very useful much wow',
            'price': 5555,
            'type': 'Other',
            'inventory': 1
        },
        {
            'id': 1,
            'name': 'Cucuka',
            'description': 'very useful much wow',
            'price': 5555,
            'type': 'Other',
            'inventory': 1
        },
        {
            'id': 1,
            'name': 'Cucuka',
            'description': 'very useful much wow',
            'price': 5555,
            'type': 'Other',
            'inventory': 1
        },
        {
            'id': 1,
            'name': 'Cucuka',
            'description': 'very useful much wow',
            'price': 5555,
            'type': 'Other',
            'inventory': 1
        },
    ]
    return (<div>
            <h1 className="center-text">Going out of Business!</h1>

            <div className="page-wrapper">
                <div className="sidebar-left middle-text">*Insert very creative add here.*</div>
                <div className="main-content">
                    <div>TODO MENU/SEARCH POINTS</div>
                    <div className="main-page-div">
                        {dummyData.map(data => {
                            return <div className="card-key-div" key={"key-" + data.id}><ProductCard product={data}/></div>
                        })}
                    </div>
                </div>
                <div className="sidebar-right middle-text">*Insert very creative add here.*</div>
            </div>
        </div>
    );
}