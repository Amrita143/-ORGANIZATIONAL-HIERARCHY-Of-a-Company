let orgData = {
    id: 1,
    children: [{
            id: 2,
            children: [
                { id: 4 },
                {
                    id: 5,
                    children: [
                        { id: 9 },
                        { id: 10 }
                    ]
                },
                { id: 6 }
            ]
        },
        {
            id: 3,
            children: [
                { id: 7 },
                { id: 8 }
            ]
        }
    ]
};

function createOrgTree(container, data) {
    const ul = document.createElement('ul');

    for (const item of data) {
        const li = document.createElement('li');
        const span = document.createElement('span');
        span.className = 'tf-nc';
        span.textContent = item.id;
        li.appendChild(span);

        if (item.children && item.children.length > 0) {
            const subTree = createOrgTree(li, item.children);
            li.appendChild(subTree);
        }

        ul.appendChild(li);
    }

    return ul;
}

function updateOrgTree() {
    const orgTreeRoot = document.getElementById('orgTreeRoot');
    orgTreeRoot.innerHTML = '';
    orgTreeRoot.appendChild(createOrgTree(orgTreeRoot, [orgData]));
}

function hireOwner() {
    const id = prompt('Enter the ID of the owner:');
    if (id) {
        orgData = {
            id: Number(id),
            children: []
        };
        updateOrgTree();
    } else {
        alert('Invalid ID.');
    }
}

function hireEmployee() {
    const id = prompt('Enter the ID of the new employee:');
    if (id) {
        const bossId = prompt('Enter the ID of the boss:');
        if (bossId) {
            try {
                hireEmployeeByIds(Number(id), Number(bossId));
                updateOrgTree();
            } catch (error) {
                alert(error.message);
            }
        } else {
            alert('Invalid boss ID.');
        }
    } else {
        alert('Invalid ID.');
    }
}

function hireEmployeeByIds(id, bossId) {
    const boss = findElementById(orgData, bossId);
    if (boss) {
        if (findElementById(orgData, id)) {
            throw new Error('Employee with the same ID already exists.');
        }
        if (!boss.children) {
            boss.children = [];
        }
        boss.children.push({ id });
    } else {
        throw new Error('Boss not found.');
    }
}

// function fireEmployee() {
//     const id = prompt('Enter the ID of the employee to fire:');
//     if (id) {
//         try {
//             fireEmployeeById(Number(id));
//             updateOrgTree();
//         } catch (error) {
//             alert(error.message);
//         }
//     } else {
//         alert('Invalid ID.');
//     }
// }

// function fireEmployeeById(id) {
//     if (orgData.id === id) {
//         throw new Error('Cannot fire the owner.');
//     }
//     const parent = findParentElement(orgData, id);
//     if (parent) {
//         parent.children = parent.children.filter(child => child.id !== id);
//     } else {
//         throw new Error('Employee not found.');
//     }
// }

function fireEmployee() {
    const id = parseInt(prompt('Enter the ID of the employee to be fired:'));
    if (!isNaN(id)) {
        try {
            fireEmployeeRecursive(orgData, id);
            updateOrgTree();
        } catch (exception) {
            alert(exception.message);
        }
    } else {
        alert('Invalid ID.');
    }
}


function fireEmployeeRecursive(data, id) {
    if (orgData.id === id) {
        throw new Error('Cannot fire the owner.');
    }
    if (!data.children) {
        return;
    }

    for (let i = 0; i < data.children.length; i++) {
        const employee = data.children[i];
        if (employee.id === id) {
            if (!employee.children) {
                data.children.splice(i, 1);
            } else {
                const manageId = parseInt(prompt('Enter the ID of the employee who will manage the subordinates:'));
                if (!isNaN(manageId)) {
                    const manageEmployee = findElementById(orgData, manageId);

                    if (!manageEmployee) {
                        throw new Error('The provided manage ID does not exist.');
                    }
                    if (!manageEmployee.children) {
                        manageEmployee.children = [];
                    }
                    manageEmployee.children = manageEmployee.children.concat(employee.children);
                    data.children.splice(i, 1);
                } else {
                    throw new Error('Invalid manage ID.');
                }
            }
            return;
        } else {
            fireEmployeeRecursive(employee, id);
        }
    }
}

// function fireEmployeeRecursive(data, id) {
//     if (orgData.id === id) {
//         throw new Error('Cannot fire the owner.');
//     }
//     if (!data.children) {
//         return;
//     }

//     for (const employee of data.children) {
//         if (employee.id === id) {
//             if (!employee.children) {
//                 data.children.splice(i, 1);
//             } else {
//                 const manageId = parseInt(prompt('Enter the ID of the employee who will manage the subordinates:'));
//                 if (!isNaN(manageId)) {
//                     const manageEmployee = findElementById(orgData, manageId);

//                     if (!manageEmployee) {
//                         throw new Error('The provided manage ID does not exist.');
//                     }

//                     manageEmployee.children = manageEmployee.children.concat(employee.children);
//                     data.children.splice(i, 1);
//                 } else {
//                     throw new Error('Invalid manage ID.');
//                 }
//             }
//             return;
//         } else {
//             fireEmployeeRecursive(employee, id);
//         }
//     }
// }









function boss(id) {
    if (orgData.id === id) {
        return -1;
    }
    const parent = findParentElement(orgData, id);
    if (parent) {
        return parent.id;
    } else {
        throw new Error('Employee not found.');
    }
}

function lowestCommonBoss(id1, id2) {
    const bossId1 = getBossId(id1);
    const bossId2 = getBossId(id2);
    if (bossId1 === bossId2) {
        return bossId1;
    }
    const level1 = level(id1);
    const level2 = level(id2);
    if (level1 > level2) {
        return lowestCommonBoss(bossId1, id2);
    } else if (level1 < level2) {
        return lowestCommonBoss(id1, bossId2);
    } else {
        return lowestCommonBoss(bossId1, bossId2);
    }
}

function getBossId(id) {
    const immediateBossId = boss(id);
    if (immediateBossId === -1) {
        throw new Error('Input ID belongs to the owner.');
    }
    return immediateBossId;
}

function level(id) {
    const employee = findElementById(orgData, id);
    if (employee) {
        let level = 0;
        let currentId = id;
        while (currentId !== orgData.id) {
            level++;
            currentId = boss(currentId);
        }
        return level;
    } else {
        throw new Error('Employee not found.');
    }
}

function findElementById(data, id) {
    if (!data) {
        return null;
    }
    if (data.id === id) {
        return data;
    }
    if (data.children) {
        for (const child of data.children) {
            const result = findElementById(child, id);
            if (result) {
                return result;
            }
        }
    }
    return null;
}

function findParentElement(data, id) {
    if (data.children) {
        for (const child of data.children) {
            if (child.id === id) {
                return data;
            }
            const result = findParentElement(child, id);
            if (result) {
                return result;
            }
        }
    }
    return null;
}

const hireOwnerBtn = document.getElementById('hireOwnerBtn');
hireOwnerBtn.addEventListener('click', hireOwner);

const hireEmployeeBtn = document.getElementById('hireEmployeeBtn');
hireEmployeeBtn.addEventListener('click', hireEmployee);

const fireEmployeeBtn = document.getElementById('fireEmployeeBtn');
fireEmployeeBtn.addEventListener('click', fireEmployee);

const bossBtn = document.getElementById('bossBtn');
bossBtn.addEventListener('click', () => {
    const id = prompt('Enter the ID of the employee:');
    if (id) {
        try {
            const immediateBoss = boss(Number(id));
            alert(`The immediate boss of employee ${id} is ${immediateBoss}`);
        } catch (error) {
            alert(error.message);
        }
    } else {
        alert('Invalid ID.');
    }
});

const lowestCommonBossBtn = document.getElementById('lowestCommonBossBtn');
lowestCommonBossBtn.addEventListener('click', () => {
    const id1 = prompt('Enter the ID of the first employee:');
    if (id1) {
        const id2 = prompt('Enter the ID of the second employee:');
        if (id2) {
            try {
                const commonBoss = lowestCommonBoss(Number(id1), Number(id2));
                alert(`The lowest common boss of employees ${id1} and ${id2} is ${commonBoss}`);
            } catch (error) {
                alert(error.message);
            }
        } else {
            alert('Invalid ID for the second employee.');
        }
    } else {
        alert('Invalid ID for the first employee.');
    }
});

updateOrgTree();