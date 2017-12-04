import { initApplication } from './application/initApplication';
import { Route, RequestOptions } from './modelLayer/annotations/ModelRoute';
import { ModelCollection } from './modelLayer/ModelCollection';
import { HasOne } from './modelLayer/annotations/HasOne';
import { Property } from './modelLayer/annotations/Property';
import { BaseModel } from './modelLayer/BaseModel';
import { HasMany } from './modelLayer/annotations/HasMany';
import { ModelSerializer } from './modelLayer/ModelSerializer';
import { Promise } from 'es6-promise';
import { IModelProperties } from './modelLayer/interfaces/IModelProperties'
import { DefferedPromise } from './modelLayer/utils/DeferredPromise'
import { User } from './application/models/User';
 

// let collection = ModelSerializer.parseCollection<User>(
//     User, 
//     [
//         {
//             name: "joo",
//             id: 10,
//             fooAcs: [{id: 3}, {id: 45}]
//         }
//     ]
// )


// let user = new User({name: "create"})

// User.create().then((resp)=>{
//     console.log("response is:")
//     console.log(resp)
// }).catch((reason)=>{
//     console.log("fail")
// })

document.addEventListener("DOMContentLoaded", function(event) { 
    initApplication()
});
