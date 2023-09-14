import SwiftUI
import shared
import Combine
import Kingfisher

struct ArtCollectionView: View {
    
    let machine: GalleryMachine
    @State var state: GalleryViewState
    let selection: Binding<String?>
    
    init(machine: GalleryMachine) {
        self.machine = machine
        self.state = machine.value
        self.selection = Binding(
            get: { machine.value.selection },
            set: machine.actions.select
        )
    }
    
    var body: some View {
        List(selection: selection) {
            Section(
                content: {
                    ForEach(state.items, id: \.id, content: ItemView.init(item:))
                },
                footer: {
                    HStack(alignment: .center) {
                        Spacer()
                        Button("load more", action: machine.actions.loadNextPage)
                            .disabled(state.isFetching)
                        Spacer()
                    }
                }
            )
        }
        .navigationTitle("Rijksmuseum")
        .onReceive(publisher(of: machine), perform: { state = $0 })
    }
}

extension GalleryViewState.Item : Identifiable {
}

fileprivate struct ItemView: View {
    
    let item: GalleryViewState.Item
    
    var body: some View {
        ZStack(alignment: .bottomLeading) {
            KFImage(item.imageUrl.flatMap(URL.init(string:)))
                .resizable()
                .aspectRatio(contentMode: .fill)
                .frame(maxWidth: .infinity, maxHeight: 88)
                .clipped()
                .opacity(0.2)
            
            Text(item.title)
                .lineLimit(1)
                .truncationMode(.tail)
                .font(.caption)
                .padding(8)
            
            Text("").hidden() // workaround for insetting the divider. Default aligns to .lastTextBaseline
        }
        .listRowInsets(.init())
    }
}
