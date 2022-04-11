package Main;

import java.util.*;

public class Trees {
    private final Set<Ghost> listGhost;   //Список объектов
//    private final HashMap<Integer, Integer> sizeGroups;     //Размеры групп
//    private HashMap<Integer, Integer> mapTo;    //соответствие групп индексам
    private final HashMap<String, Ghost> Tmap1;    //карта по столбцу A
    private final HashMap<String, Ghost> Tmap2;    //карта по столбцу B
    private final HashMap<String, Ghost> Tmap3;    //карта по столбцу C
    public Trees() {
//        sizeGroups = new HashMap<>();
        Tmap1 = new HashMap<>();
        Tmap2 = new HashMap<>();
        Tmap3 = new HashMap<>();
        listGhost = new LinkedHashSet<>();
    }

    private void removeGhost(Ghost remG, Ghost ghost) {
        List<Node> tmpTree = remG.getTree();
        ghost.add(remG);
        for (Node n : tmpTree) {    //пройти по всем позициям и заменить объект при наличии старого
            if (Tmap1.get(n.getA()) == remG) Tmap1.put(n.getA(), ghost);
            if (Tmap2.get(n.getB()) == remG) Tmap2.put(n.getB(), ghost);
            if (Tmap3.get(n.getC()) == remG) Tmap3.put(n.getC(), ghost);
        }
        listGhost.remove(remG);     //урать объект из списка
    }

    public void AddNode(Node node) {    //тестовое добавление в список
        boolean cka = Tmap1.containsKey(node.getA());
        boolean ckb = Tmap2.containsKey(node.getB());
        boolean ckc = Tmap3.containsKey(node.getC());
        if (!cka && !ckb && !ckc) {     //Если нет еще совпадений
            Ghost ghost = new Ghost();  //создать новый объект
            listGhost.add(ghost);       //добавить его в список объектов
            ghost.add(node);            //добавить узел в объект
            if (node.getA().length() > 0) Tmap1.put(node.getA(), ghost);
            if (node.getB().length() > 0) Tmap2.put(node.getB(), ghost);
            if (node.getC().length() > 0) Tmap3.put(node.getC(), ghost);
        } else if (cka && !ckb && !ckc) {   //Если совпадение только с одной позицией
            Ghost ghost = Tmap1.get(node.getA());
            ghost.add(node);            //добавить узел в объект
            if (node.getB().length() > 0) Tmap2.put(node.getB(), ghost);  //установить объект для других ключей
            if (node.getC().length() > 0) Tmap3.put(node.getC(), ghost);
        } else if (!cka && ckb && !ckc) {
                Ghost ghost = Tmap2.get(node.getB());
                ghost.add(node);
            if (node.getA().length() > 0) Tmap1.put(node.getA(), ghost);
            if (node.getC().length() > 0) Tmap3.put(node.getC(), ghost);
        } else if (!cka && !ckb && ckc) {
                Ghost ghost = Tmap3.get(node.getC());
                ghost.add(node);
            if (node.getA().length() > 0) Tmap1.put(node.getA(), ghost);
            if (node.getB().length() > 0) Tmap2.put(node.getB(), ghost);
        } else if (cka && ckb && !ckc) {    //Если сразу в двух позициях найден
                Ghost ghost = Tmap1.get(node.getA());
                ghost.add(node);
            if (node.getC().length() > 0) Tmap3.put(node.getC(), ghost);
                Ghost removeIt = Tmap2.get(node.getB());
                if (removeIt != ghost) {    //и они разные объекты
                    removeGhost(removeIt, ghost);   //переместить один объект в другой
                }
        } else if (cka && !ckb && ckc) {
                Ghost ghost = Tmap1.get(node.getA());
                ghost.add(node);
            if (node.getB().length() > 0) Tmap2.put(node.getB(), ghost);
                Ghost removeIt = Tmap3.get(node.getC());
                if (removeIt != ghost) {
                    removeGhost(removeIt, ghost);
                }
        } else if (!cka && ckb && ckc) {
                Ghost ghost = Tmap2.get(node.getB());
                ghost.add(node);
            if (node.getA().length() > 0) Tmap1.put(node.getA(), ghost);
                Ghost removeIt = Tmap3.get(node.getC());
                if (removeIt != ghost) {
                    removeGhost(removeIt, ghost);
                }
        } else {    //Если все есть все три позиции
            Ghost ghostA = Tmap1.get(node.getA());
            Ghost ghostB = Tmap2.get(node.getB());
            Ghost ghostC = Tmap3.get(node.getC());
            if (ghostA == ghostB && ghostA == ghostC) { //но все они это один и тот же объект
                ghostA.add(node);
            } else if (ghostA == ghostB && ghostB != ghostC) {  //один из них отличается от двух других
                ghostA.add(node);
                removeGhost(ghostC, ghostA);
            } else if (ghostA == ghostC && ghostA != ghostB) {
                ghostA.add(node);
                removeGhost(ghostB, ghostA);
            } else if (ghostB == ghostC && ghostB != ghostA) {
                ghostB.add(node);
                removeGhost(ghostA, ghostB);
            } else {                        //Все объекта три разные
                ghostA.add(node);
                removeGhost(ghostB, ghostA);
                removeGhost(ghostC, ghostA);
            }
        }

    }

    public List<Map.Entry<Ghost, Integer>> prepareGetGroups() {     //Получение массива по группам и подготовка массивов
        HashMap<Ghost, Integer> entryMap = new HashMap<>();   //Подготавливаем карту объектов с их размерами
        for (Ghost gh : listGhost) {    //помещаем объекты в карту
            if (gh.getSize() > 1) {     //только те, размер которых превышает 1
                entryMap.put(gh, gh.getSize());
            }
        }
        return entryMap.entrySet().stream().sorted((a, b) -> {      //возвращаем отсортированный массив объектов
            return Integer.compare(b.getValue(), a.getValue());
        }).toList();
    }

}
