import React, {useState} from 'react';
import _ from 'lodash';
import './App.css';

function App() {
    const terrainTypes = ["sea", "grassland", "hills", "mountain"];
    const [activeTerrain, setActiveTerrain] = useState("sea");
    return (<main>
        <div className="map-view">

        </div>
        <div className="controls">
            <div className="set-terrain">
                <h3>Set Terrain</h3>
                {_.map(terrainTypes, (terrainType: string) => {
                    return <button
                        onClick={() => setActiveTerrain(terrainType)}
                        className={[terrainType,
                            activeTerrain === terrainType ?
                                "active" :
                                undefined].join(" ")}>
                        {terrainType.toLocaleUpperCase()}
                    </button>;
                })}
            </div>
        </div>
    </main>);
}

export default App;
