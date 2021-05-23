import numpy as np
import matplotlib
import matplotlib.pyplot as plt

def zad1():
    x = np.linspace(-8,10,100)
    y1 = lambda x: 1 / (1 + (abs(x-2)/1))
    y2 = lambda x: 1 / (1 + (abs(x-2)/0.25))
    y3 = lambda x: 1 / (1 + (abs(x-2)/4))

    Y1 = [y1(xk) for xk in x]
    Y2 = [y2(xk) for xk in x]
    Y3 = [y3(xk) for xk in x]

    plt.plot(x, Y1, label='s=1')
    plt.plot(x, Y2, label='s=0.25')
    plt.plot(x, Y3, label='s=4')
    plt.xlabel('x')
    plt.ylabel('y')
    plt.title('w=2')
    plt.legend()
    plt.show()

def poltZad1(X,Y,s):
    plt.plot(X,Y, label=f's={s}')

def zad2():
    with open(r'zad7-dataset.txt', 'r') as file:
        chunk = np.loadtxt(r'zad7-dataset.txt')
        data = np.array(chunk)
    X = data[:, 0]
    Y = data[:, 1]

    label = [calcLabel(round(a),round(b),round(c)) for a,b,c in zip(data[:, 2], data[:, 3], data[:, 4])]
    colors = ['red', 'green', 'blue']
    plt.scatter(X, Y, c=label, cmap=matplotlib.colors.ListedColormap(colors))
    plt.show()

def zad4():
    with open(r'zad7-dataset.txt', 'r') as file:
        chunk = np.loadtxt(r'zad7-dataset.txt')
        data = np.array(chunk)
    X = data[:, 0]
    Y = data[:, 1]

    label = [calcLabel(round(a), round(b), round(c)) for a, b, c in zip(data[:, 2], data[:, 3], data[:, 4])]
    colors = ['red', 'green', 'blue', 'black']
    X = np.append(X, [0.1245879, 0.13484, 0.65788, 0.37272, 0.283290, 0.864802], axis=0)
    Y = np.append(Y, [0.73569, 0.25606, 0.257328, 0.2641221, 0.761375, 0.73928], axis=0)
    label = np.append(label, [3,3,3,3,3,3])
    plt.scatter(X, Y, c=label, cmap=matplotlib.colors.ListedColormap(colors))
    plt.xlabel('wx')
    plt.ylabel('wy')
    plt.show()

    sx = [0.0976259, 0.1142219, 14.373895, 0.1317, 0.134012, 0.081703, 0.105264, 0.171326]
    sy = [0.20455548, 0.427717, 0.54641, 0.18645, 0.13362, 0.257397, 0.185373, 0.11763]

    plt.plot(np.linspace(1, 6, 8), sx, label='|sx|')
    plt.plot(np.linspace(1, 6, 8), sy, label='|sy|')
    plt.xlabel('index of first hidden layer neuron')
    plt.ylabel('|sx|,|sy|')
    plt.legend()
    plt.show()

def calcLabel(a,b,c):
    if a == 1:
        return 0
    elif b == 1:
        return 1
    else:
        return 2


def main():
    #zad1()
    #zad2()
    zad4()

if __name__ == '__main__':
    main()