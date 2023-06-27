# -ORGANIZATIONAL-HIERARCHY-Of-a-Company
Please read the "ass2.pdf" to understand the project in detail.

public interface OrgHierarchy {

public OrgHierarchy(); Initializes an empty organization 

public boolean isEmpty(); Returns true if the organization is empty. 

public int size(); Returns the number of employees in the organization 

public int level(int id) ; Returns the level of the employee with ID=id 

public void hireOwner(int id) ; Adds the first employee of the organization, which we call the owner. There is only one owner in an org who cannot be deleted 

public void hireEmployee(int id, int bossid) ; 
Adds a new employee whose ID is id. This employee will work under an existing employee 
whose ID is bossid (note that this automatically decides the
level of id, it is one more than that of bossid). Your code should throw an
exception if the id already exists in the OrgHierarcy 

public void fireEmployee(int id); Deletes an employee who does not manage any other employees.

public void fireEmployee(int id, int manageid);
Deletes an employee (id) who might manage other employees. Manageid is
another employee who works at the same level as id. All employees working under
id will now work under manageid 

public int boss(int id) ; Returns the immediate boss, the employee. Returns -1 if id is the owner’s ID 

public int lowestCommonBoss(int id1, int id2) ;
outputs the ID of the employee A who is a boss of both id1 and id2, and
among all such persons has the largest level. In other words, we want to find
the common boss who is lowest in the hierarchy in the company. If one of the
input ids is the owner, output -1 .

}

