import shared
import SwiftUI

struct ContentView: View {
    var body: some View {
        NavigationView {
            GitHubUserListScreen()
                .navigationBarTitle(Text(MR.strings().app_name.localize()))
        }
        .navigationViewStyle(.stack)
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
