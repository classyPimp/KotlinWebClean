import { initApplication } from './application/initApplication';
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
