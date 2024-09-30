package com.example.posapplication.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.posapplication.core.sharepreference.SharePreferenceUtil.currentLoginAccountId

@Composable
fun NoteGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: Any = if (currentLoginAccountId != -1) NoteRoute else LoginRoute,
//    startDestination: Any = StartRoute,
    navActions: NoteGraphNavigationActions =
        remember(navController) {
            NoteGraphNavigationActions(navController)
        },
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable<LoginRoute> {
//            LoginScreen(
//                onLoginSuccess = { navActions.navigationToNote() },
//                onSignUp = { navActions.navigateToRegister() },
//            )
        }
        composable<RegisterRoute> {
//            RegisterScreen(
//                onSignIn = { navActions.navigateToLogin() },
//            )
        }
        composable<NoteRoute> {
//            NoteScreen(onCreateItem = {
//                navActions.navigateToAddEditScreen()
//            }, onEditItem = { note ->
//                navActions.navigateToAddEditScreen(note.noteId)
//            })
        }
        composable<AddEditNoteRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<AddEditNoteRoute>()
//            AddEditNoteScreen(
//                noteId = route.noteId,
//                onBackPress = { navActions.onBackPressed() },
//            )
        }
        composable<StartRoute> {
//            StartScreen()
        }
    }
}
