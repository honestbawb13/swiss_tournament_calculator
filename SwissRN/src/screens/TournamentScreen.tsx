import React from 'react';
import { View, StyleSheet } from 'react-native';
import { Appbar, Text, Button, useTheme } from 'react-native-paper';
import type { NativeStackScreenProps } from '@react-navigation/native-stack';
import type { RootStackParamList } from '../../App';

type Props = NativeStackScreenProps<RootStackParamList, 'Tournament'>;

export default function TournamentScreen({ route, navigation }: Props) {
  const theme = useTheme();
  const id = route.params?.id ?? 'new';

  return (
    <View style={styles.container}>
      <Appbar.Header mode="center-aligned">
        <Appbar.BackAction onPress={() => navigation.goBack()} />
        <Appbar.Content title={id === 'new' ? 'New Tournament' : 'Tournament'} />
      </Appbar.Header>
      <View style={styles.content}>
        <Text variant="titleLarge" style={{ marginBottom: 12 }}>
          {id === 'new' ? 'Create a Tournament' : `Tournament #${id}`}
        </Text>
        <Text style={{ opacity: 0.8, marginBottom: 24 }}>
          This screen will connect to the native Kotlin engine for pairings,
          rounds, and standings. For now, it is a placeholder UI.
        </Text>
        <Button mode="contained" onPress={() => {}}>
          Add Players (placeholder)
        </Button>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: '#0B0B10' },
  content: { padding: 16 },
});

