import Combine
import shared
import SwiftUI

func publisher<S, A>(of machine: LuxMachine<S, A>) ->  AnyPublisher<S, Never> {
    let publisher = Deferred(createPublisher: {
        let subject = PassthroughSubject<S, Never>()
        let cancel = machine.collect(
            onEach: subject.send,
            onCompletion: { _ in subject.send(completion: .finished) }
        )
        
        return subject.handleEvents(receiveCancel: cancel)
    })
    
    return publisher.eraseToAnyPublisher()
}

private struct RijksMachineKey: EnvironmentKey {
    static let defaultValue: RijksMachine = Platform().toMachine()
}

extension EnvironmentValues {
    var machine: RijksMachine {
        get { self[RijksMachineKey.self] }
        set { self[RijksMachineKey.self] = newValue }
    }
}
