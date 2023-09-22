import SwiftUI
import shared
import Combine
import Kingfisher

struct DetailsView: View {
    
    let machine: DetailsViewMachine
    @State var state: DetailsViewState
    
    init(machine: DetailsViewMachine) {
        self.machine = machine
        self.state = machine.value
    }
    
    var body: some View {
        List {
            KFImage(state.imageUrl.flatMap(URL.init(string:)))
                .resizable()
                .aspectRatio(contentMode: .fit)
                .frame(maxWidth: .infinity, idealHeight: 300)
                .clipped()
                .padding([.top, .bottom])
                .listRowSeparator(.hidden, edges: [.top])
                .listRowSeparator(.visible, edges: [.bottom])
            
            Text(state.description_)
                .listRowSeparator(.hidden)
        }
        .listStyle(.plain)
        .navigationTitle(state.title)
        .navigationBarBackButtonHidden()
        .navigationBarItems(leading: Button(
            action : machine.actions.deselect,
            label: { Image(systemName: "arrow.left") }
        ))
        .onReceive(publisher(of: machine), perform: { state = $0 })
    }
}
