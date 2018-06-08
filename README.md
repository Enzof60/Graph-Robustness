# Graph-Robustness

The purpose of this project was to develop an application  that would be able to monitor and police a network of connected computers.
The application should be able to spot a potential attack on the network and act accordingly so that the network doesn't get hacked 
or altered in any way that may cause the network to become corrupt or unstable.

By researching papers on Graph Theory, Percolation Theory and Binomial distribution. An attempt was made to develop 
an application in java that would create a random graph from the number of nodes the user inputs. It then uses Binomial distribution 
to assign edges at random to create the random graph. 

Once the random graph has been outputed by the application the user can pick any node at random and mark it as "infected" or "under attack by a hacker".
The application then removes any connection to the infected node to the rest of the graph. This node is then put in quarantine until notified. 

The application then remodels the graph so that all paths are still intact and there is no loss in the degree of connectivity between nodes.

The final goal would be to develop an autonomous system that will monitor and police any network which may hold valuable information that if corrupted
 may affect a decision make process. 

By use technology such as Blockchain to store information and to record any changes made to the data and the use of well known and highly researched topics such as
 Graph Theory, Percolation Theory and Binomial distribution to remodel the network i think that such an application  can be developed 