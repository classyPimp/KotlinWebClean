import { Migration } from '../models/Migration';
import { Model } from '../models/Model';
import autobind from 'autobind-decorator';
import { BaseReactComponent } from '../../../javascript/reactUtils/BaseReactComponent';
import {Link} from 'react-router-dom'
import * as React from 'react';

export class GeneratorsIndex extends BaseReactComponent {

    render() {
        return <div>
            <p>
                <Link to="/generators/migrations">
                    generate migration
                </Link>
            </p>
            <p>
                <Link to="generators/model">
                    generate model files
                </Link>
            </p>
            <div>
                <button onClick={this.generateJooq}>
                    jooq generate
                </button>
            </div>
            <div>
                <button onClick={this.migrate}>
                    db:migrate
                </button>
            </div>
            <div>
                <button onClick={this.rollback}>
                    db:rollback
                </button>
            </div>
        </div>
    }

    @autobind
    generateJooq(){
        console.log("should generate")
        Model.generateJooq({}).then(()=>{
            alert("success")
        })
    }

    @autobind
    migrate(){
        Migration.migrate().then((message)=>{
            alert(message["message"])
        })
    }

    @autobind
    rollback(){
        Migration.rollback().then((message)=>{
            alert(message["message"])
        })
    }
}