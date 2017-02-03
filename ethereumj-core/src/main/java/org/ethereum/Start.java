package org.ethereum;

import org.ethereum.cli.CLIInterface;
import org.ethereum.config.SystemProperties;
import org.ethereum.facade.Ethereum;
import org.ethereum.facade.EthereumFactory;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URISyntaxException;

/**
 * @author Roman Mandeleil
 * @since 14.11.2014
 */
public class Start {

    public static void main(String args[]) throws IOException, URISyntaxException {
        CLIInterface.call(args);

        if (!SystemProperties.getDefault().blocksLoader().equals("")) {
            SystemProperties.getDefault().setSyncEnabled(false);
            SystemProperties.getDefault().setDiscoveryEnabled(false);
        }

        final Ethereum ethereum = EthereumFactory.createEthereum();
//        ethereum.exitOn(2287000);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                ethereum.close();
            }
        });

        if (!SystemProperties.getDefault().blocksLoader().equals(""))
            ethereum.getBlockLoader().loadBlocks();
    }

}
