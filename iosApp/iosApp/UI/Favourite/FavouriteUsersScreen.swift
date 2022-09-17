//
//  FavouriteUsersScreen.swift
//  iosApp
//
//  Created by Imre Kaszab on 2022. 09. 17..
//  Copyright © 2022. orgName. All rights reserved.
//

import SwiftUI

struct FavouriteUsersScreen: View {
    @StateObject private var observableViewModel = FavouriteUsersViewModelImpl()
    @State private var showConfirmDialog = false

    var body: some View {
        VStack(alignment: .center) {
            Spacer()
            List {
                ForEach(observableViewModel.itemList, id: \.id) { item in
                    NavigationLink(destination: GitHubUserDetailsScreen(userName: item.login)) {
                        GitHubUserRow(item: item)
                    }
                }
            }
            .listStateModifier(observableViewModel.itemList.isEmpty) {
                Text("We don't have any content, sorry 😔")
            }
            .listStateModifier(!observableViewModel.error.isEmpty) {
                Text("Something went wrong 🤯 \n\n" + observableViewModel.error)
            }
            Spacer()
        }
        .navigationTitle("Favourite users")
        .toolbar {
            if !observableViewModel.itemList.isEmpty {
                HStack {
                    Button(action: { showConfirmDialog.toggle() }) {
                        Image(systemName: "pip.remove")
                    }
                }
            }
        }
        .confirmationDialog("Remove all user", isPresented: $showConfirmDialog, actions: {
            HStack {
                Button("Remove all users") {
                    observableViewModel.viewModel.deleteAllUser()
                }
            }
        })
    }
}
