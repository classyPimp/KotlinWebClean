
import { DemoComponent } from './components/Demo';
import {render} from 'react-dom'
import { Router, Route } from 'react-router-dom';
import { ApplicationComponent } from './components/ApplicationComponent';
import * as React from 'react';
import { BrowserRouter } from 'react-router-dom'
import { ModelRegistrator } from './ModelRegistrator'

export function initApplication() {

    ModelRegistrator.run()

    render(
        <BrowserRouter>
            <Route path="/" component={ApplicationComponent}/>
        </BrowserRouter>,
        document.getElementById("app"))
}



