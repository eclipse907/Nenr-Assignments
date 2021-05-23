import numpy as np
import matplotlib.pyplot as plt
import math as Math


def plot(path):
    with open(path, 'r') as file:
        chunk = np.loadtxt(path, delimiter=",")
        data = np.array(chunk)

        Xs = data[:, 0]
        Ys = data[:, 1]
        Zs = data[:, 2]

        fig = plt.figure()
        ax = fig.add_subplot(111, projection='3d')

        surf = ax.plot_trisurf(Xs, Ys, Zs)
        plt.show()


def plotSigmoids(path):
    with open(path, 'r') as file:
        chunk = np.loadtxt(path, delimiter=",")
        data = np.array(chunk)

        a = data[:, 0]
        b = data[:, 1]
        c = data[:, 2]
        d = data[:, 3]
        fig, axs = plt.subplots(5, 4, figsize=(15, 15))

        counter = 0
        for i in [0, 2, 4, 6, 8]:
            X = np.linspace(-4, 4, 100)
            y1 = [1 / (1 + Math.exp(b[i] * (x - a[i]))) for x in X]
            y2 = [1 / (1 + Math.exp(c[i] * (x - d[i]))) for x in X]

            y3 = [1 / (1 + Math.exp(b[i + 1] * (x - a[i + 1]))) for x in X]
            y4 = [1 / (1 + Math.exp(c[i + 1] * (x - d[i + 1]))) for x in X]

            axs[counter, 0].plot(X, y1)
            title0 = 'Rule ' + str((i + 1))
            axs[counter, 0].set(xlabel='x', ylabel='Sigmoid', title=title0)
            axs[counter, 0].set_ylim(0, 1)

            axs[counter, 1].plot(X, y2)
            title1 = 'Rule ' + str((i + 1))
            axs[counter, 1].set(xlabel='y', ylabel='Sigmoid', title=title1)
            axs[counter, 1].set_ylim(0, 1)

            axs[counter, 2].plot(X, y3)
            title2 = 'Rule ' + str((i + 2))
            axs[counter, 2].set(xlabel='x', ylabel='Sigmoid', title=title2)
            axs[counter, 2].set_ylim(0, 1)

            axs[counter, 3].plot(X, y4)
            title3 = 'Rule ' + str((i + 2))
            axs[counter, 3].set(xlabel='y', ylabel='Sigmoid', title=title3)
            axs[counter, 3].set_ylim(0, 1)

            counter += 1
        plt.subplots_adjust(wspace=0.8, hspace=0.8)
        plt.show()


def plotPlotErrors(path_grad, path_stoh):
    data_grad = []
    with open(path_grad, 'r') as file:
        chunk = np.loadtxt(path_grad, delimiter=",")
        data_grad = np.array(chunk)
    grad_iter = data_grad[:, 0]
    errors_grad = data_grad[:, 1]
    data_stoh = []
    with open(path_stoh, 'r') as file:
        chunk = np.loadtxt(path_stoh, delimiter=",")
        data_stoh = np.array(chunk)
    stoh_iter = data_stoh[:, 0]
    errors_stoh = data_stoh[:, 1]

    plt.figure()
    plt.plot(grad_iter, errors_grad, label='Batch')
    plt.xlabel("Iteration")
    plt.ylabel("Error")
    plt.show()

    plt.figure()
    plt.plot(stoh_iter, errors_stoh, label='SGD')
    plt.xlabel("Iteration")
    plt.ylabel("Error")
    plt.show()


def plotPlotErrorsWithLR(path_worst, path_average, path_best):
    data_worst = []
    with open(path_worst, 'r') as file:
        chunk = np.loadtxt(path_worst, delimiter=",")
        data_worst = np.array(chunk)
    iter_worst = data_worst[:, 0]
    errors_worst = data_worst[:, 1]

    data_average = []
    with open(path_average, 'r') as file:
        chunk = np.loadtxt(path_average, delimiter=",")
        data_average = np.array(chunk)
    iter_average = data_average[:, 0]
    errors_average = data_average[:, 1]

    data_best = []
    with open(path_best, 'r') as file:
        chunk = np.loadtxt(path_best, delimiter=",")
        data_best = np.array(chunk)
    iter_best = data_best[:, 0]
    errors_best = data_best[:, 1]

    plt.figure()
    plt.plot(iter_worst, errors_worst, label='Eta fuzzy = 0.005, Eta function= 0.02')

    plt.plot(iter_average, errors_average, label='Eta fuzzy = 0.001, Eta function= 0.01')

    plt.plot(iter_best, errors_best, label='Eta fuzzy = 0.0000001, Eta function= 0.000001')
    plt.xlabel("Iteration")
    plt.ylabel("Error")
    plt.legend()
    plt.show()


def main():
    root = "D:/Backup Tin/Tin backup/Documents/College/Nenr/Labosi/DZ6"
    smallest = root + '/error_batch_small.txt'
    optimal = root + '/error_batch.txt'
    biggest = root + '/error_batch_big.txt'
    plotPlotErrorsWithLR(biggest, optimal, smallest)


if __name__ == '__main__':
    main()
