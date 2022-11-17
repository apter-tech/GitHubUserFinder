import SwiftUI

struct ContentView: View {
    var body: some View {
        NavigationView {
            GitHubUserListScreen()
                .navigationBarTitle(Text("GitHubUserFinder"))
        }
        .navigationViewStyle(.stack)
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
