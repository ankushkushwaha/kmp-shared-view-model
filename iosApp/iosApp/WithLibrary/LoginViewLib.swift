//
//  LoginView2.swift
//  iosApp
//
//  Created by Ankush Kushwaha on 09/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import KMPObservableViewModelSwiftUI
import Shared

struct LoginViewLib: View {
    @StateViewModel var viewModel = LoginSharedViewModelLib()

    @State private var username: String = ""
    @State private var password: String = ""

    @State private var showPassword: Bool = false
    
    @State private var showDetail: Bool = false
    
    var body: some View {
        NavigationStack {
            VStack(spacing: 20) {
                
                TextField("Email", text: $username)
                    .textContentType(.emailAddress)
                    .keyboardType(.emailAddress)
                    .autocapitalization(.none)
                    .padding()
                    .background(Color(.secondarySystemBackground))
                    .cornerRadius(8)
                
                HStack {
                    if showPassword {
                        TextField("Password", text: $password)
                    } else {
                        SecureField("Password", text: $password)
                    }
                    Button(action: {
                        showPassword.toggle()
                    }) {
                        Image(systemName: showPassword ? "eye.slash" : "eye")
                            .foregroundColor(.gray)
                    }
                }
                .padding()
                .background(Color(.secondarySystemBackground))
                .cornerRadius(8)
                
                // Login Button
                Button(action: viewModel.startFetch) {
                    if viewModel.viewStateValue.isLoading?.boolValue ?? false {
                        ProgressView()
                            .progressViewStyle(CircularProgressViewStyle())
                    } else {
                        Text("Login")
                            .foregroundColor(.white)
                            .padding()
                            .frame(maxWidth: .infinity)
                            .background(Color.blue)
                            .cornerRadius(8)
                    }
                }
                .disabled(!(viewModel.viewStateValue.isValidCredentials?.boolValue ?? false))
                .opacity(viewModel.viewStateValue.isValidCredentials?.boolValue ?? false ? 1 : 0.5)
                
                ChildView(name: $username)
            }
            .padding()
            
            .navigationDestination(isPresented: $showDetail) {
                ContentView()
            }

            .onChange(of: username) { _ in
                print("updateViewModel username")
                viewModel.setUsername(usernme: username)
            }
            .onChange(of: password) { _ in
                print("updateViewModel password")
               viewModel.setPassword(password: password)
            }
            .onChange(of: showDetail) { _ in
                print("updateViewModel showDetail")
                viewModel.setPushToHomeScreen(pushToHomeScreen: showDetail)
            }
            .onChange(of: viewModel.viewStateValue.pushToHomeScreen) { _ in
                showDetail = viewModel.viewStateValue.pushToHomeScreen?.boolValue ?? false
            }
        }
    }
}
