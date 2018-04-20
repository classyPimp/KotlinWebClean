package websockets.endpoints

import javax.websocket.*

class FrontWebsocketEndpoint: Endpoint() {

    override fun onOpen(session: Session, config: EndpointConfig) {
        session.requestURI
        session.addMessageHandler(MessageHandler.Whole<String> {

        })
    }

    override fun onClose(session: Session, closeReason: CloseReason) {

    }

    override fun onError(session: Session?, thr: Throwable?) {

    }


}