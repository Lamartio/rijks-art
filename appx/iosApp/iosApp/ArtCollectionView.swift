import SwiftUI
import shared
import Combine
import Kingfisher

struct ArtCollectionView: View {
    
    @Environment(\.machine) var machine: RijksMachine
    @State var items: [ArtCollection.ArtObject] = []
    @State var isRefreshing: Bool = false
    @State var selection: String?
    
    var body: some View {
        List(selection: $selection) {
            Section(
                content: {
                    ForEach(items, id: \.id) { item in
                        ZStack(alignment: .bottomLeading) {
                            KFImage(item.headerImage.url.flatMap(URL.init(string:)))
                                .resizable()
                                .aspectRatio(contentMode: .fill)
                                .frame(maxWidth: .infinity, maxHeight: 56)
                                .clipped()
                                .opacity(0.2)
                            
                            Text(item.title)
                                .lineLimit(1)
                                .truncationMode(.tail)
                                .font(.caption)
                                .padding(8)
                            
                            Text("").hidden() // workaround for instetting the divider. Default aligns to .lastTextBaseline
                        }
                    }
                    .listRowInsets(.init())
                },
                footer: {
                    HStack(alignment: .center) {
                        Spacer()
                        Button("load more", action: machine.actions.appendCollection)
                            .disabled(isRefreshing)
                        Spacer()
                    }
                }
            )
        }
        .navigationTitle("Hey! \(selection ?? "")")
        .onReceive(publisher(of: machine).map(\.collections.items), perform: { items = $0 })
        .onReceive(publisher(of: machine).map(\.fetchingCollection.state), perform: { isRefreshing = $0 is LuxAsyncExecuting })
        .onReceive(publisher(of: machine).map(\.selection), perform: { selection = $0 })
        .onChange(of: selection, perform: machine.actions.select(id:))
    }
}

extension ArtCollection.ArtObject : Identifiable {
}
