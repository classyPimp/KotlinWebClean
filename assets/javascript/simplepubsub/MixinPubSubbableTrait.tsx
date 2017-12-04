import { AnyConstructor } from '../modelLayer/utils/AnyConstructor';


export function MixinPubSubbableTrait<TBase extends AnyConstructor>(Base: TBase) {

    return class PubSubbableTrait extends Base {

        subscribers: {
            [id:string]: Array<any>
        }

        addSubscriberUnder(namespace: string, subscriber: any){
            if(!this.subscribers) {
                this.subscribers = {}
            }
            if (!this.subscribers[namespace]) {
                this.subscribers[namespace] = new Array<any>()
            }
            if (this.hasSubscriberIn(subscriber, this.subscribers[namespace])) {
                throw (`subscriber: ${subscriber} - has subscribed alredy`)
            } else {
                this.subscribers[namespace].push(subscriber)
            }
        }

        removeSubscriberFrom(namespace: string, subscriberToRemove: any) {
            if (this.subscribers[namespace]) {
                this.subscribers[namespace] = this.subscribers[namespace].filter((subscriber) => subscriber !== subscriberToRemove);
            }
        }

        hasSubscriberIn(subscriberToTest: any, array: Array<any>) {       
          for(let i = 0; i < array.length; i++) {
            if(array[i] === subscriberToTest) {
              return true;
            }
          }
          return false; 
        }

        publish(namespace: string, command: string, ...args: Array<any> ) {
            this.subscribers
            if (this.subscribers) {
                if (this.subscribers[namespace]) {
                    this.subscribers[namespace].forEach((subscriber)=>{
                        subscriber[command](...args)
                    })
                }
            }
        }

    }

}