# git file modification 
## terms
- modified(Changed File)
- untracked(New File)
- staged(Added)
- unmodified
## Flow
- Add (git add gitcheat.md)//(git add .)// To Add All Files
- Commit (git commit -m "Some Message")
- push (git push origin main) // to reflect the changes in the remote repo(github repo)
- git status(to view the current status)


# Working with a repo either possible by git clone <link> or manually adding the origin 

- Check if the folder is git repo
- ls -a (if .git not displayed then init)
- git init
- git remote add origin <link>
- git remote -v (to verify remote)
- git branch (to check branch)// default branches - main , other branches -master,etc.
- git branch -M main (to change the branch name to main)
- git push origin main // git push -u origin main( next time you may use git push only)

# Working with branches

- git checkout <branchname>
- git checkout -b <new branchname> (to create a branch and move to it.)
- git branch -d <branchname>

# Branch Merge
## W1
- git diff <branchname> // to compare the branches
- git merge <branchname>
## W2
- Create Pull Request

# Pull Command

- git pull orogin main (to implement the remote repo changes in the local repo)

# Resolving mergeconflicts
- An event when git is unable to resolve differences between the two branches
- We need to resolve the changes manually

# Undoing Changes 

- git log(to check all commits)//q to quit
## Staged changes
- git reset <filename>
- git reset
## Commited changes (for one commit)
- git reset HEAD~1
## Commited changes for many commits
- git reset <commit hash>// the change to which we wanna move , copy its has from the git log dropdown and then paste it here
- git reset --hard <commithash>// to remove the changes in code from the files as well 