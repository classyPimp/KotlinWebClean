import { HilfHundApplicationComponent } from './components/ApplicationComponent';
import { GeneratorsIndex } from './components/GeneratorsIndex';
import { ApplicationComponent } from '../../javascript/application/components/ApplicationComponent';
import { BrowserRouter } from "react-router-dom";
import * as React from 'react';
import {render} from 'react-dom'
import {Router, Route} from 'react-router-dom'
import { MigrationsCreate } from './components/migrations/MigrationsCreate';
import { ModelGenerationIndexComponent } from './components/modelGeneration/ModelGenerationIndexComponent'
export function HilfHundInit() {
    render(
        <BrowserRouter>
            <div>
                <Route exact path="/hilfhund" component={HilfHundApplicationComponent}/>
                <Route exact path="/generators" component={GeneratorsIndex}/> 
                <Route exact path="/generators/migrations" component={MigrationsCreate}/>
                <Route exact path="/generators/model" component={ModelGenerationIndexComponent}/>
            </div>
        </BrowserRouter>,
        document.getElementById("app")
    )
    
}