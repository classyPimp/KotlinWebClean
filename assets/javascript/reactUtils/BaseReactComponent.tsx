import * as React from 'react';

export class BaseReactComponent<PROPS_TYPE = any, STATE_TYPE = any> extends React.Component<PROPS_TYPE, STATE_TYPE> {

    init() {

    }

    constructor(props?: any) {
        super(props)
        this.init()
    }

}